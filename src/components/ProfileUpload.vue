<template>
  <div class="flex flex-col py-4">
    <label
      for="profile-upload"
      class="cursor-pointer flex items-center justify-center "
    >
      <img :src="(previewUrl as string)" class="w-32 h-32 rounded-full object-cover" />
    </label>
    <input id="profile-upload" type="file" class="hidden" accept="image/*" @change="onFileChange" />
  </div>
</template>

<script lang="ts">
import { ref, onMounted } from 'vue';

export default {
  props: {
    file:File,
  },
  setup(props, { emit }) {

    const previewUrl = ref<string | null>(props.file ? URL.createObjectURL(props.file) : 'https://fakeimg.pl/300x400');

    const onFileChange = (event: Event) => {
      const input = event.target as HTMLInputElement;
      if (input.files && input.files[0]) {
        emit('update:file', input.files[0]);
        previewUrl.value = URL.createObjectURL(input.files[0]);
      }
    };

    return {
      previewUrl,
      onFileChange,
    };
  },
};
</script>
