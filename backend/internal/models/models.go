package models

import (
	"github.com/mmcdole/gofeed"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type Company struct {
	Title   string `bson:"title" json:"title"`
	HtmlUrl string `bson:"html_url" json:"html_url"`
	XmlUrl  string `bson:"xml_url" json:"xml_url"`
}

type Article struct {
	Id      primitive.ObjectID `bson:"_id" json:"id"`
	Company Company     `bson:"company" json:"company"`
	Article gofeed.Item        `bson:"article" json:"article"`
}

type Blog struct {
	Company     Company       `bson:"company"`
	BlogTitle   string        `bson:"blog_title"`
	Description string        `bson:"description"`
	Link        string        `bson:"link"`
	FeedLink    string        `bson:"feed_link"`
	Image       *gofeed.Image `bson:"image"`
}