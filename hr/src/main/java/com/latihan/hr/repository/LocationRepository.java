package com.latihan.hr.repository;

import java.util.List;

import com.latihan.hr.entity.Location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long>{
    
    @Query(value = "SELECT * FROM LOCATIONS l " +
                   "LEFT JOIN COUNTRIES c " +
                   "ON (l.COUNTRY_ID = c.COUNTRY_ID) " +
                   "WHERE LOWER(l.STREET_ADDRESS) " +
                   "LIKE LOWER(CONCAT(CONCAT('%', :pStreetAddress), '%')) OR LOWER(l.POSTAL_CODE) " +
                   "LIKE LOWER(CONCAT(CONCAT('%', :pPostalCode), '%')) OR LOWER(l.CITY) " +
                   "LIKE LOWER(CONCAT(CONCAT('%', :pCity), '%')) OR LOWER(l.STATE_PROVINCE) " +
                   "LIKE LOWER(CONCAT(CONCAT('%', :pStateProvince), '%')) OR LOWER(c.COUNTRY_NAME) " +
                   "LIKE LOWER(CONCAT(CONCAT('%', :pCountryName), '%'))", nativeQuery = true)
    List<Location> getByAllCategorie(@Param("pStreetAddress") String streetAddress,
                                     @Param("pPostalCode") String postalCode,
                                     @Param("pCity") String city,
                                     @Param("pStateProvince") String stateProvince,
                                     @Param("pCountryName") String countryName);

    default List<Location> getByAllCategories(String all) {
        return getByAllCategorie(all, all, all, all, all);
    }

    @Query(value = "SELECT * FROM LOCATIONS l" +
            " LEFT JOIN " + " COUNTRIES c " +
            " ON l.COUNTRY_ID = "
            + "c.COUNTRY_ID WHERE LOWER(c.COUNTRY_NAME) LIKE LOWER(CONCAT(CONCAT('%', :pCountryName), '%'))", nativeQuery = true)
    List<Location> findByCountryNameContainingIgnoreCas(@Param("pCountryName") String countryName);

}
