package db

import (
	"context"
	"os"

	"github.com/2307vivek/blogs/utils"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var MongoDB *mongo.Database

func InitDatabase() {
	mongoUri := os.Getenv("MONGODB_URI")
	mongodbName := os.Getenv("MONGODB_NAME")

	if mongoUri == "" {
		panic("Cannot get mongodb uri")
	}

	serverApi := options.ServerAPI(options.ServerAPIVersion1)
	opts := options.Client().ApplyURI(mongoUri).SetServerAPIOptions(serverApi)

	client, err := mongo.Connect(context.Background(), opts)
	utils.FailOnError(err, "Cannot create mongodb client")

	defer func() {
		if err = client.Disconnect(context.TODO()); err != nil {
			panic(err)
		}
	}()

	var result bson.M
	if err := client.Database(mongodbName).RunCommand(context.TODO(), bson.D{{Key: "ping", Value: 1}}).Decode(&result); err != nil {
		utils.FailOnError(err, "Cannot ping the database")
	}
	MongoDB = client.Database(mongodbName)
}
