<template>
  <div class="flex flex-col py-4">
    <label
      for="profile-upload"
      class="cursor-pointer flex items-center justify-center "
    >
      <img :src="(previewUrl as string)|| 'https://fakeimg.pl/300x400'" class="w-32 h-32 rounded-full object-cover" />
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
    const previewUrl = ref<string | null>(null);

    const onFileChange = (event: Event) => {
      const input = event.target as HTMLInputElement;
      if (input.files && input.files[0]) {
        emit('update:file', input.files[0]);
        previewUrl.value = URL.createObjectURL(input.files[0]);
      }
    };

    onMounted(() => {
      // Clear the preview when the component is mounted
      previewUrl.value = null;
    });

    return {
      previewUrl,
      onFileChange,
    };
  },
};
</script>
