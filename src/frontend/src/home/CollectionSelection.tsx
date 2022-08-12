import { Link } from "react-router-dom";

const callouts = [
  {
    name: "Sunglasses",
    description: "Shield from bright lights and awkward eye contact",
    imageSrc: "/images/sunglasses.jpg",
    imageAlt: "Gold-rimmed sunglasses sitting on a reflective surface.",
    href: "/products/accessories/sunglasses",
  },
  {
    name: "Hoodies",
    description: "Cosy and warm",
    imageSrc: "/images/hoodie.jpg",
    imageAlt: "Hooded man with his hands in his pockets looking at the ground.",
    href: "/products/clothing/hoodies",
  },
  {
    name: "Trainers",
    description: "Stylish and essential",
    imageSrc: "/images/trainers.jpg",
    imageAlt: "Out-stretched arm holding high-top trainers by the laces.",
    href: "/products/footwear/trainers",
  },
];

export const CollectionSelection = () => {
  return (
    <div className="bg-gray-100">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="max-w-2xl mx-auto py-16 sm:py-24 lg:py-32 lg:max-w-none">
          <h2 className="text-2xl font-extrabold text-gray-900">Collections</h2>

          <div className="mt-6 space-y-12 lg:space-y-0 lg:grid lg:grid-cols-3 lg:gap-x-6">
            {callouts.map((callout) => (
              <div key={callout.name} className="group relative">
                <div className="relative w-full h-80 bg-white rounded-lg overflow-hidden group-hover:opacity-75 sm:aspect-w-2 sm:aspect-h-1 sm:h-64 lg:aspect-w-1 lg:aspect-h-1">
                  <img
                    src={callout.imageSrc}
                    alt={callout.imageAlt}
                    className="w-full h-full object-center object-cover"
                  />
                </div>
                <h3 className="mt-6 text-sm text-gray-500">
                  <Link to={callout.href}>
                    <span className="absolute inset-0" />
                    {callout.name}
                  </Link>
                </h3>
                <p className="text-base font-semibold text-gray-900">
                  {callout.description}
                </p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
