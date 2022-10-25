package com.library.citadel_library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportStatisticDTO {

   private Integer booksNumber;

   private Integer authorNumber;

   private Integer publisherNumber;

   private Integer categoryNumber;

   private Integer loansNumber;

   private Integer bookNotReturnNumber;

   private Integer expiredBookNumber;

   private Integer membersNumber;
}
