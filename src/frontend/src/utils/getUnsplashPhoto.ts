import axios from "axios";
import { ACCESS_KEY } from "../dev-images/api-keys";

type imageSize = "raw" | "full" | "regular" | "small" | "thumb";

interface ApiResponse {
  id: string;
  urls: {
    raw: string;
    full: string;
    regular: string;
    small: string;
    thumb: string;
  };
}

export default async function getUnsplashPhoto(
  unsplashId: string,
  size?: imageSize
) {
  const response = await axios.get<ApiResponse>(
    `https://api.unsplash.com/photos/${unsplashId}`,
    {
      headers: {
        Authorization: `Client-ID ${ACCESS_KEY}`,
        "Accept-Version": "v1",
      },
    }
  );

  const imageSize = size ? size : "small";

  return response.data.urls[imageSize];
}
