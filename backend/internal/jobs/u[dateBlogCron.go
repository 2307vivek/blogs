package jobs

import "github.com/robfig/cron"

func RunUpdateBlogCron() {
	c := cron.New()

	c.AddFunc("* 11 * * *", func() {
        CreateJobs()
  })

    // Start the Cron job scheduler
    c.Start()
}