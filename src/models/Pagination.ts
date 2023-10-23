export default interface Pagination<T> {
  data: T[];
  page: number;
  pageSize: number;
  totalPage: number;
  totalElements: number;
}