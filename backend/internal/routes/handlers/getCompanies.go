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

func GetCompanies(c *gin.Context) {
	pageStr := c.Query("page")

	page, err := utils.ValidatePage(pageStr)
	if err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"msg": "Invalid page number."})
		return
	}

	collection := db.MongoDB.Collection(db.BlogsCollection)

	blogs := []models.Blog{}

	paginationOpts := utils.CreatePagination(20, page).Paginate()
	sortOpts := options.Find().SetSort(bson.D{{Key: "company.title", Value: 1}})
	filter := bson.D{}

	cursor, err := collection.Find(context.TODO(), filter, paginationOpts, sortOpts)

	if err != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"msg": "Some error occured"})
		return
	}

	if err = cursor.All(context.TODO(), &blogs); err != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"msg": "Some error occured"})
		return
	}

	if len(blogs) == 0 {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"msg": "No more data"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"page": page, "blogs": blogs})
}