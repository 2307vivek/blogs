package jobs

import (
	"context"
	"log"

	"github.com/2307vivek/blogs/internal/db"
	"github.com/2307vivek/blogs/internal/models"
	"github.com/2307vivek/blogs/utils"
	"github.com/gilliek/go-opml/opml"
	"github.com/mmcdole/gofeed"
)

func CreateJobs() {
	companies := parseFile()

	blogs_collections := db.MongoDB.Collection(db.BlogsCollection)
	articles_collections := db.MongoDB.Collection(db.ArticleCollection)

	for _, company := range companies {
		feed, err := getFeed(company.XmlUrl)
		if err != nil {
			log.Println("Failed to parse ", company.XmlUrl)
			continue
		}

		blog := models.Blog{Company: company, BlogTitle: feed.Title, Description: feed.Description, Link: feed.Link, FeedLink: feed.FeedLink, Image: feed.Image}
		blogRes, err := blogs_collections.InsertOne(context.Background(), blog)
		if err != nil {
			log.Println("Unable to insert blog ", blog)
		}

		articles := []interface{}{}
		for _, item := range feed.Items {
			article := Article{BlogID: blogRes.InsertedID, Article: item, Company: company}
			articles = append(articles, article)
		}

		_, err = articles_collections.InsertMany(context.Background(), articles)
		if err != nil {
			log.Println("Unable to insert article for blog ", blog)
		}
	}
}

func getFeed(url string) (*gofeed.Feed, error) {
	fp := gofeed.NewParser()

	feed, err := fp.ParseURL(url)
	return feed, err
}

func parseFile() []models.Company {
	docs, err := opml.NewOPMLFromFile("../../engineering_blogs.opml")
	utils.FailOnError(err, "Failed to load opml file.")

	outlines := docs.Outlines()

	companies := []models.Company{}

	for _, outline := range outlines[0].Outlines {
		companies = append(companies, models.Company{Title: outline.Title, HtmlUrl: outline.HTMLURL, XmlUrl: outline.XMLURL})
	}
	return companies
}

type Article struct {
	BlogID  interface{} `bson:"blog_id"`
	Company interface{}
	Article interface{}
}
