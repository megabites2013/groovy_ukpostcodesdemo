package com.efiab.springdockerjib.repository

import com.efiab.springdockerjib.model.PostCode
import org.springframework.data.repository.PagingAndSortingRepository

interface IPostCodeRepository extends PagingAndSortingRepository<PostCode, Integer> {}
