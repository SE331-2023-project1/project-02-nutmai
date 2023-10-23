package com.nutmai.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> {
  private int page;
  private int pageSize;
  private int totalPage;
  private long totalElements;
  private T data;
}
