<script lang="ts" setup>
import router from "@/router";
import { getAllFilesService, uploadFileService } from "@/services/userService";
import { useUserStore } from "@/stores";
import nProgress from "nprogress";
import { storeToRefs } from "pinia";
import { ref } from "vue";
import { v4 as uuidv4 } from "uuid";
import { useToast } from "vue-toast-notification";
import Navbar from "@/components/Navbar.vue";
let files = ref([]);
const currentUser = storeToRefs(useUserStore()).getCurrentUser;
const $toast = useToast();
nProgress.start();
getAllFilesService()
  .then((res) => {
    files.value = res;
  })
  .catch(() => {
    router.push({ name: "network-error" });
  })
  .finally(() => {
    nProgress.done();
  });

let fileToUpload = ref(null);
let fileDescription = ref('');

async function handleUploadFile() {

  if (!fileToUpload.value) {
    return;
  }

  const formData = new FormData();
  formData.append('file', fileToUpload.value);
  formData.append('description', fileDescription.value);

  try {
    await uploadFileService({
      id: uuidv4(),
      name: fileToUpload.value.name,
      description: fileDescription.value,
      createdByUserId: currentUser.value.id,
      type: fileToUpload.value.type,
      // encode file to base64 string
      data: await new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(fileToUpload.value);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
      }),
    });

    // Optionally, update the list of files
    getAllFilesService().then((res) => {
      files.value = res.data;
      $toast.success("File uploaded successfully", { position: "top-right" });
    }).finally(() => {
      fileToUpload.value = null;
      fileDescription.value = '';
      window.location.reload();
    })
  } catch (error) {
    // Handle errors
    $toast.error("File upload failed", { position: "top-right" });
    console.error('File upload failed:', error);
  }
}

async function handleOpenFile(file) {
  const blob = await fetch(file.data).then((r) => r.blob());
  const url = window.URL.createObjectURL(blob);
  // set tab target to _blank to open in new tab
  // set tab name to file name
  window.open(url, '_blank').name = file.name;
}

</script>

<template>
  <Navbar />
  <div class="flex flex-col gap-4">
    <div v-if="currentUser.role === 'TEACHER' || currentUser.role === 'ADMIN'">
      <form @submit.prevent="handleUploadFile" class="my-4 p-4 border rounded-lg shadow-md flex gap-2 flex-col">
        <input type="text" v-model="fileDescription" placeholder="Write announcement..." class="mb-2 p-2 border rounded" />
        <input type="file" accept="*" @change="fileToUpload = $event.target.files[0]" class="mb-2" />
        <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-700">Upload File</button>
      </form>
    </div>
    <!-- Any user can see files uploaded -->
    <div class="my-4">
      <ul>
        <li v-for="(file,index) in files" :key="file.id" class="mb-4">
          <p class="text-gray-600">{{ file.description }}</p>
          <button @click="() => handleOpenFile(file)" class="text-blue-500 hover:underline">{{ file.name }}</button>
        </li>
      </ul>
    </div>
  </div>
</template>

