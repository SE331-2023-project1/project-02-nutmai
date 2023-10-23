import type Pagination from "@/models/Pagination";
import type { UserModel } from "@/models/userModel";
import axiosClient from "@/utils/axiosClient";

export async function getStudentsService(perPage?: number, page?: number) {
  if (perPage && page)
    return axiosClient.get<Pagination<UserModel>>("/users/getStudents?page=" + (page - 1) + "&pageSize=" + perPage).then((res) => {
      return res.data;
    });
  return axiosClient.get<Pagination<UserModel>>("/users/getStudents?page=0&pageSize=1000").then((res) => res.data);
}


export async function getStudentById(id: string){
  return axiosClient.get<UserModel>('/users/getUserById?id=' + id).then(res => res.data)
}