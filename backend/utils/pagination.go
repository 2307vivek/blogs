package utils

import "go.mongodb.org/mongo-driver/mongo/options"

func CreatePagination(limit, page int) *pagination {
   return &pagination{
      limit: int64(limit),
      page:  int64(page),
   }
}

func (mp *pagination) Paginate() *options.FindOptions {
   l := mp.limit
   skip := mp.page*mp.limit - mp.limit
   fOpt := options.FindOptions{Limit: &l, Skip: &skip}

   return &fOpt
}

type pagination struct {
   limit int64
   page int64
}