package routes

import (
	"fmt"
	"net/http"

	"github.com/2307vivek/blogs/internal/routes/handlers"
	"github.com/gin-gonic/gin"
)

func Routes(port int) {
	router := gin.Default()

	router.GET("/", func(ctx *gin.Context) {
		ctx.JSON(http.StatusOK, gin.H{"msg": "THis is a response."})
	})

	router.GET("/articles/:company", handlers.FindArticlesByCompany)
	router.GET("/articles", handlers.GetLatestArticles)

	router.GET("/blogs", handlers.GetCompanies)

	router.Run(fmt.Sprintf(":%d", port))
}
