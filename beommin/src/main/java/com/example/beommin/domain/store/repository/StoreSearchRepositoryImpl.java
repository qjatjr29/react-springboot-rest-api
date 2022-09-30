package com.example.beommin.domain.store.repository;

import static com.example.beommin.domain.store.entity.QStore.store;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.domain.store.dto.SearchStoreRequest;
import com.example.beommin.domain.store.entity.Sorting;
import com.example.beommin.domain.store.entity.Store;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

public class StoreSearchRepositoryImpl implements StoreSearchRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public StoreSearchRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Store> searchStore(SearchStoreRequest searchStoreRequest, Pageable pageable) {

    JPAQuery<Store> storeJPAQuery = jpaQueryFactory
        .select(store)
        .from(store)
        .where(keywordListContains(searchStoreRequest.getKeyword())
            .or(addressContains(searchStoreRequest.getRegion()))
            .or(categoryEq(searchStoreRequest.getCategoryList()))
        )
        .orderBy(sortingEq(searchStoreRequest.getSorting()), store.createdAt.desc())
        .offset(pageable.getOffset()).limit(pageable.getPageSize() + 1);

    List<Store> storeList = storeJPAQuery.fetch();
    return new PageImpl<>(storeList, pageable, storeList.size());
  }

  private BooleanBuilder keywordListContains(String keyword) {
    BooleanBuilder builder = new BooleanBuilder();
    if (isEmpty(keyword)) {
      return builder;
    }
    String[] splitedKeyword = keyword.split(" ");
    for (String value : splitedKeyword) {
      builder.and(store.name.contains(value));
      builder.or(store.description.contains(value));
      builder.or(store.user.name.contains(value));
    }
    return builder;
  }

  private BooleanExpression addressContains(String region) {

    if(isEmpty(region)) return null;

    return isEmpty(region) ? null : store.address.addressName.contains(region);
  }

  private BooleanBuilder categoryEq(List<Category> categories) {
    if (ObjectUtils.isEmpty(categories)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    for (Category category : categories) {
      builder.and(store.categoryList.any().category.eq(category));
    }
    return builder;
  }

  private OrderSpecifier<?> sortingEq(Sorting sorting) {
    return isEmpty(sorting) ? Sorting.최신순.expression() : sorting.expression();
  }

}
