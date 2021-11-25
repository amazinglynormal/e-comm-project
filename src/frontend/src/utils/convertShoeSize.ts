const sizeEquivalents: { [index: string]: { [index: string]: number } } = {
  "6": { eur: 39, us: 6.5 },
  "6.5": { eur: 40, us: 7 },
  "7": { eur: 40.5, us: 7.5 },
  "7.5": { eur: 41, us: 8 },
  "8": { eur: 41.5, us: 8.5 },
  "8.5": { eur: 42, us: 9 },
  "9": { eur: 42.5, us: 9.5 },
  "9.5": { eur: 43.5, us: 10 },
  "10": { eur: 44, us: 10.5 },
  "10.5": { eur: 44.5, us: 11 },
  "11": { eur: 46, us: 11.5 },
  "12": { eur: 47, us: 12.5 },
  "13": { eur: 48.5, us: 13.5 },
};

export default function convertShoeSize(size: number, region: "eur" | "us") {
  return sizeEquivalents[`${size}`][region];
}
