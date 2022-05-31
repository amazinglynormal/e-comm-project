export default function formatDate(date: string) {
  // 2022-05-31T12:32:45.877+00:00  <--- format expected
  // 2022-05-31 <--- format returned

  return date.split("T")[0];
}
