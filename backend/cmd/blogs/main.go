package main

import (
	"github.com/2307vivek/blogs/internal/db"
	"github.com/2307vivek/blogs/internal/jobs"
	"github.com/2307vivek/blogs/utils"
	"github.com/joho/godotenv"
)

func main() {
	err := godotenv.Load("../../.env")
	utils.FailOnError(err, "Failed to load .env")
	db.InitDatabase()
	jobs.CreateJobs()
}
