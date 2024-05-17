package handlers

import (
	"context"

	"net/http"

	"github.com/2307vivek/blogs/internal/db"
	"github.com/2307vivek/blogs/internal/models"
	"github.com/2307vivek/blogs/utils"

	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo/options"
)

func FindArticlesByCompany(c *gin.Context) {
	companyName := c.Param("company")
	pageStr := c.Query("page")

	page, err := utils.ValidatePage(pageStr)
	if err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"msg": "Invalid page number."})
		return
	}

	if companyName == "" {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"msg": "Company name not provided."})
		return
	}

	articles, err := FindArticlesByCompanyName(companyName, page)
	if err != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"msg": "Some error occured"})
	}

	if len(articles) == 0 {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"msg": "No data."})
		return
	}

	c.JSON(http.StatusOK, gin.H{"page": page, "articles": articles})
}

func FindArticlesByCompanyName(companyName string, page int) ([]models.Article, error) {
	collection := db.MongoDB.Collection(db.ArticleCollection)
	filter := bson.D{{Key: "company.title", Value: companyName}}

	articles := []models.Article{}

	paginationOpts := utils.CreatePagination(10, page).Paginate()
	sortOpts := options.Find().SetSort(bson.D{{Key: "article.publishedparsed", Value: -1}})
	cursor, err := collection.Find(context.TODO(), filter, paginationOpts, sortOpts)

	if err != nil {
		return nil, err
	}

	if err = cursor.All(context.TODO(), &articles); err != nil {
		return nil, err
	}

	return articles, nil
}
