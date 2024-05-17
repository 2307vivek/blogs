package main

import (
	"github.com/2307vivek/blogs/internal/auth"
	"github.com/2307vivek/blogs/internal/db"
	"github.com/2307vivek/blogs/internal/jobs"

	"github.com/2307vivek/blogs/internal/jobs"

	"github.com/2307vivek/blogs/internal/routes"
	"github.com/2307vivek/blogs/utils"
	"github.com/joho/godotenv"
)

func main() {
	err := godotenv.Load("../../.env")
	utils.FailOnError(err, "Failed to load .env")
	db.InitDatabase()
	go jobs.RunUpdateBlogCron()

	auth.InitFirebase()

	routes.Routes(8080)
}
