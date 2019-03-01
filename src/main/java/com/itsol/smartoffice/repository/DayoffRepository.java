package com.itsol.smartoffice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itsol.smartoffice.model.DayOff;
import com.itsol.smartoffice.model.Users;

@Repository
public interface DayoffRepository extends JpaRepository<DayOff, Integer>, PagingAndSortingRepository<DayOff, Integer> {
	
	@Query(value = "SELECT D FROM DAYOFF D")
	Page<DayOff> findAll(Pageable pageable);
	
	List<DayOff> findByUsers(Users users);

	DayOff findByDayOffId(Integer dayOffId);
	
	@Query(value = "SELECT D FROM DAYOFF D WHERE D.status = :status")
	Page<DayOff> findByStatus(Pageable pageable, @Param("status") String status);

	@Query(value = "SELECT D FROM DAYOFF D")
	Page<DayOff> getThreeDayOff(Pageable pageable);
	
	@Query(value = "SELECT D FROM DAYOFF D, PROJECTS P "
			+ "WHERE P.projectId = :projectId "
				+ "AND D.dateStart BETWEEN P.timeStart AND P.timeEnd")
	List<DayOff> getByProjectTimeStart(@Param("projectId") Integer projectId);
	
}
