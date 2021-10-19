import { AxiosResponseHeaders } from "axios";

function extractJwtTokenFromResponseHeaders(headers: AxiosResponseHeaders) {
  return headers["authorization"].split("Bearer ")[1];
}

export default extractJwtTokenFromResponseHeaders;
