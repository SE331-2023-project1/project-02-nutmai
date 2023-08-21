import { type StudentModel } from "@/models/studentModel";
import axiosClient from "@/utils/axiosClient";

export async function getStudentsService(perPage?: number, page?: number) {
  if (perPage && page)
    return axiosClient.get<StudentModel[]>("/students?_limit=" + perPage + "&_page=" + page).then((res) => {
      return { data: res.data, total: res.headers["x-total-count"] as number };
    });
  return axiosClient.get<StudentModel[]>("/students").then((res) => res.data);
}


export async function getStudentById(id: string){
  return axiosClient.get<StudentModel>('/students/' + id).then(res => res.data)
}