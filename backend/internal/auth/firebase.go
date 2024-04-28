package auth

import (
	"context"
	"path/filepath"

	"firebase.google.com/go/v4/auth"
	"github.com/2307vivek/blogs/utils"
	"google.golang.org/api/option"

	firebase "firebase.google.com/go/v4"
)

func InitFirebase() *auth.Client {
	serviceAccountKeyPath, err := filepath.Abs("../../service_account_key.json")
	utils.FailOnError(err, "Failed to load service account key file.")

	opt := option.WithCredentialsFile(serviceAccountKeyPath)

	firebaseApp, err := firebase.NewApp(context.Background(), nil, opt)
	utils.FailOnError(err, "Unable to initialize a firebase app.")

	authClient, err := firebaseApp.Auth(context.Background())
	utils.FailOnError(err, "Unable to create auth client")

	return authClient
}
