package utils

import "strconv"

func ValidatePage(pageStr string) (int, error) {
	if pageStr == "" {
		pageStr = "1"
	}

	page, err := strconv.Atoi(pageStr)

	return page, err
}