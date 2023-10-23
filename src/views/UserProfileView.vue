<script lang="ts" setup>
import Navbar from "@/components/Navbar.vue";
import ProfileUpload from "@/components/ProfileUpload.vue";
import router from "@/router";
import { updateUserService } from "@/services/userService";
import { useUserStore } from "@/stores/userStore";
import axiosClient from "@/utils/axiosClient";
import { storeToRefs } from "pinia";
import { useToast } from "vue-toast-notification";

const currentUser = storeToRefs(useUserStore()).getCurrentUser;

let file: File | undefined = dataURLtoFile(currentUser.value.img, "profile.png");

let firstName: string | undefined = currentUser.value.name;
let lastName: string | undefined = currentUser.value.surname;
let department: string | undefined = currentUser.value.department;

const $toast = useToast();

function dataURLtoFile(dataurl: any, filename: any) {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[arr.length - 1]), 
        n = bstr.length, 
        u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {type:mime});
}

async function handleUpdateAccount() {
  if (!file) {
    $toast.error("Please upload a profile picture", { position: "top-right" });
    return;
  }
  const reader = new FileReader();
  reader.onload = async () => {
    const base64 = reader.result;
    const user = await updateUserService({
      id: currentUser.value.id,
      img: base64 as string,
      name: firstName as string,
      surname: lastName as string,
      department: department as string,
    });
    if (user.token) {
      localStorage.setItem("user", JSON.stringify(user));
      $toast.success("Account updated successfully", { position: "top-right" });
      return window.location.href = "/"
    }
    $toast.error("Account creation failed", { position: "top-right" });
  };
  reader.readAsDataURL(file as File);
}
</script>

<template>
  <Navbar />
  <div class="container max-w-lg mx-auto flex-1 flex flex-col items-center justify-center px-2">
    <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
      <h1 class="text-3xl text-center">Your Profile</h1>
      <ProfileUpload :file="file" @update:file="file = $event" />
      <div class="flex gap-2">
        <div class="flex flex-col">
          <label class="block text-grey-darker text-sm font-bold mb-2">
            First Name
          </label>
          <input
            type="text"
            class="block border border-grey-light w-full p-3 rounded mb-4"
            name="firstname"
            v-model="firstName"
            placeholder="First Name"
          />
        </div>
        <div class="flex flex-col">
          <label class="block text-grey-darker text-sm font-bold mb-2">
            Last Name
          </label>
          <input
            type="text"
            class="block border border-grey-light w-full p-3 rounded mb-4"
            name="lastname"
            v-model="lastName"
            placeholder="Last Name"
          />
        </div>
      </div>
      <div class="flex flex-col">
        <label class="block text-grey-darker text-sm font-bold mb-2">
          Department
        </label>
        <input
          type="text"
          class="block border border-grey-light w-full p-3 rounded mb-4"
          name="department"
          v-model="department"
          placeholder="Department"
        />
      </div>
      <button
        type="submit"
        class="w-full text-center py-3 rounded bg-green-500 text-white hover:bg-green-700 focus:outline-none my-1"
        v-on:click="handleUpdateAccount"
      >
        Save profile
      </button>
    </div>
  </div>
</template>
