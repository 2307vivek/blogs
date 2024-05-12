package handlers

import (
	"context"

	"net/http"

	"github.com/2307vivek/blogs/internal/db"
	"github.com/2307vivek/blogs/internal/models"
	"github.com/2307vivek/blogs/utils"

	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/bson"
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

	collection := db.MongoDB.Collection(db.ArticleCollection)
	filter := bson.D{{Key: "company.title", Value: companyName}}

	articles := []models.Article{}

	opts := utils.CreatePagination(10, page).Paginate()
	cursor, err := collection.Find(context.TODO(), filter, opts)

	if err != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"msg": "Some error occured"})
		return
	}

	if err = cursor.All(context.TODO(), &articles); err != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"msg": "Some error occured"})
		return
	}

	if len(articles) == 0 {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"msg": "Invalid page number."})
		return
	}

	c.JSON(http.StatusOK, gin.H{"articles": articles})
}
