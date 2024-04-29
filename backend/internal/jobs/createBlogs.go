package jobs

import (
	"context"
	"log"

	"github.com/2307vivek/blogs/internal/db"
	"github.com/2307vivek/blogs/utils"
	"github.com/gilliek/go-opml/opml"
	"github.com/mmcdole/gofeed"
)

func CreateJobs() {
	companies := parseFile()

	for _, company := range companies {
		feed, err := getFeed(company.XmlUrl)
		if err != nil {
			log.Println("Failed to parse ", company.XmlUrl)
			continue
		}
		_, err = db.MongoDB.Collection("blogs").InsertOne(context.Background(), feed)

		if err != nil {
			log.Println("Failed to insert ", company.XmlUrl)
		}
	}
}

func getFeed(url string) (*gofeed.Feed, error) {
	fp := gofeed.NewParser()

	feed, err := fp.ParseURL(url)
	return feed, err
}

func parseFile() []Company {
	docs, err := opml.NewOPMLFromFile("../../engineering_blogs.opml")
	utils.FailOnError(err, "Failed to load opml file.")

	outlines := docs.Outlines()

	companies := []Company{}

	for _, outline := range outlines[0].Outlines {
		companies = append(companies, Company{Title: outline.Title, HtmlUrl: outline.HTMLURL, XmlUrl: outline.XMLURL})
	}
	return companies
}

type Company struct {
	Title   string `bson:"title"`
	HtmlUrl string `bson:"html_url"`
	XmlUrl  string `bson:"xml_url"`
}
