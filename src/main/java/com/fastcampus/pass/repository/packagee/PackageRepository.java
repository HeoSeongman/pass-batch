package com.fastcampus.pass.repository.packagee;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Integer> {

    List<PackageEntity> findByCreatedAtAfter(LocalDateTime dateTime, Pageable pageable);


    @Transactional
    @Modifying // INSERT, UPDATE, DELETE 문으로 데이터를 변경할 때 사용
    @Query(value = "UPDATE PackageEntity p " + // PackageEntity 형식의 변수 p 선언
            "SET p.count = :count, " + // p 의 필드 count 에 매개변수 count 를 대입
            "p.period = :period " + // p 의 필드 period 에 매개변수 period 를 대입
            "WHERE p.packageSeq = :packageSeq") // p 의 필드 packageSeq 에 매개변수 packageSeq 를 대입 후 찾기
    int updateCountAndPeriod(@Param("packageSeq") Integer packageSeq, @Param("count") Integer count, @Param("period") Integer period);
    // For queries with named parameters you need to use provide names for method parameters.
    // Use @Param for query method parameters, or when on Java 8+ use the javac flag -parameters
    // 쿼리에 명명된 매개 변수가 있는 경우 메소드 매개 변수에 이름을 제공해야 한다. 쿼리 메서드 매개변수에 @Param 을 사용하거나 Java 8+에서 javac 플래그 -parameters 를 사용하면 된다.

}
