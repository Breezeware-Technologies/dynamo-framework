package net.breezeware.dynamo.util.date;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;

public class AgeCalculator {

    /**
     * Calculate the age in years as the difference between 2 dates.
     * @param birthDate   the birth date or the starting date
     * @param currentDate the current date or the end date
     * @return the age, in number of years
     */
    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate != null && currentDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    /**
     * Calculate the age as the number of years passed since a given start date.
     * @param birthDateInCalendar the birth date
     * @return the age in number of years
     */
    public static int calculateAge(Calendar birthDateInCalendar) {
        if (birthDateInCalendar != null) {
            LocalDate birthDate = birthDateInCalendar.getTime().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate currentDate = LocalDate.now();

            if (birthDate != null && currentDate != null) {
                return Period.between(birthDate, currentDate).getYears();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}