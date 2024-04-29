package middleware

import (
	"context"
	"net/http"

	"github.com/2307vivek/blogs/internal/auth"
	"github.com/gin-gonic/gin"
)

func AuthenticateToken(c *gin.Context) {
	token := c.GetHeader("Authorization")

	if token == "" {
		c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "Unathorized"})
		return
	}

	decodedToken, err := auth.AuthClient.VerifyIDToken(context.Background(), token)

	if err != nil {
		c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "Invalid Token"})
		return
	}

	c.Set("decodedToken", decodedToken)
	c.Next()
}