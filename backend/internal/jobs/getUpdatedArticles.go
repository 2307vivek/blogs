package jobs

import "github.com/mmcdole/gofeed"

func GetUpdatedArticles(old map[string]gofeed.Item, new []*gofeed.Item) []*gofeed.Item {
	if len(old) == 0 {
		return new
	}
	updated := []*gofeed.Item{}

	for _, newItem := range new {
		_, exists := old[newItem.Title]
		if !exists {
			updated = append(updated, newItem)
		} else {
			break
		}
	}
	return updated
}
