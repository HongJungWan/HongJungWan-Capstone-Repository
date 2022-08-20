package com.example.demo.repository.custom;

import com.example.demo.domain.entity.Car;
import com.example.demo.domain.entity.QCar;
import com.example.demo.domain.entity.QUser;
import com.example.demo.domain.value.EnrollStatus;
import com.example.demo.domain.value.CarSearch;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CarCustomRepositoryImpl implements CarCustomRepository{

    private final EntityManager em;

    public CarCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    // 5,12 test
    public void save(Car car) {
        em.persist(car);
    }

    public Car findOne(Long id) {
        return em.find(Car.class, id);
    }

    public Page<Car> findAll(CarSearch carSearch, Pageable pageable) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QCar car = QCar.car;
        QUser user = QUser.user;

        QueryResults<Car> results = query
                .select(car)
                .from(car)
                .join(car.user, user)
                .where(statusEq(carSearch.getEnrollStatus()), nameLike(carSearch.getUserName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Car> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression statusEq(EnrollStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return QCar.car.status.eq(statusCond);
    }

    private BooleanExpression nameLike(String userName) {
        if (!StringUtils.hasText(userName)) {
            return null;
        }
        return QUser.user.name.like(userName);
    }

}
