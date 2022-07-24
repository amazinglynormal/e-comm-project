import { Dispatch, SetStateAction } from "react";
import { Popover } from "@headlessui/react";
import { MenuIcon } from "@heroicons/react/outline";

import { FlyoutMenu } from "./FlyoutMenu";
import { CartLink } from "./CartLink";
import { Link } from "react-router-dom";

const navigation = {
  categories: [
    {
      name: "Clothing",
      featured: [
        {
          name: "T-shirts",
          href: "/products/clothing/tshirts",
          imageSrc: "/images/tshirtcollection.jpg",
          imageAlt: "Assorted folded t-shirts on a wooden table",
        },
        {
          name: "Jeans",
          href: "/products/clothing/jeans",
          imageSrc: "/images/jeans.jpg",
          imageAlt: "The back pocket of a pair of blue denim jeans.",
        },
        {
          name: "Hoodies",
          href: "/products/clothing/hoodies",
          imageSrc: "/images/hoodie.jpg",
          imageAlt:
            "Hooded man with his hands in his pockets looking at the ground.",
        },
        {
          name: "Shirts",
          href: "/products/clothing/shirts",
          imageSrc: "/images/shirt.jpg",
          imageAlt: "A clothing rack with three shirts on hangers.",
        },
        {
          name: "Jackets",
          href: "/products/clothing/jackets",
          imageSrc: "/images/jacket.jpg",
          imageAlt:
            "close up of a pair of hands zipping up a green winter jacket.",
        },
      ],
    },
    {
      name: "Footwear",
      featured: [
        {
          name: "Boots",
          href: "/products/footwear/boots",
          imageSrc: "/images/boots.jpg",
          imageAlt: "Brown work boots hanging by the laces.",
        },
        {
          name: "Trainers",
          href: "/products/footwear/trainers",
          imageSrc: "/images/trainers.jpg",
          imageAlt: "Out-stretched arm holding high-top trainers by the laces.",
        },
        {
          name: "Shoes",
          href: "/products/footwear/shoes",
          imageSrc: "/images/shoes.jpg",
          imageAlt: "Unlaced brown shoes.",
        },
      ],
    },
    {
      name: "Accessories",
      featured: [
        {
          name: "Belts",
          href: "/products/accessories/belts",
          imageSrc: "/images/belt.jpg",
          imageAlt: "A rolled up brown belt sitting on a black wire rack.",
        },
        {
          name: "Sunglasses",
          href: "/products/accessories/sunglasses",
          imageSrc: "/images/sunglasses.jpg",
          imageAlt: "Gold-rimmed sunglasses sitting on a reflective surface.",
        },
        {
          name: "Ties",
          href: "/products/accessories/ties",
          imageSrc: "/images/ties.jpg",
          imageAlt: "A wall of ties of various colours and patterns.",
        },
        {
          name: "Wallets",
          href: "/products/accessories/wallets",
          imageSrc: "/images/wallets.jpg",
          imageAlt: "A display of leather wallets.",
        },
      ],
    },
  ],
};

interface Props {
  setOpen: Dispatch<SetStateAction<boolean>>;
}

export const DesktopNavigation = ({ setOpen }: Props) => {
  return (
    <div className="bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="border-b border-gray-200">
          <div className="h-16 flex items-center justify-between">
            {/* Logo (lg+) */}
            <div className="hidden lg:flex-1 lg:flex lg:items-center">
              <Link to="/">
                <p className="font-pacifico text-4xl text-indigo-600">Eire</p>
              </Link>
            </div>

            <div className="hidden h-full lg:flex">
              {/* Flyout menus */}
              <Popover.Group className="px-4 bottom-0 inset-x-0 z-10">
                <div className="h-full flex justify-center space-x-8">
                  {navigation.categories.map((category) => (
                    <FlyoutMenu key={category.name} category={category} />
                  ))}
                </div>
              </Popover.Group>
            </div>

            {/* Mobile menu and search (lg-) */}
            <div className="flex-1 flex items-center lg:hidden">
              <button
                type="button"
                className="-ml-2 bg-white p-2 rounded-md text-gray-400"
                onClick={() => setOpen(true)}
              >
                <span className="sr-only">Open menu</span>
                <MenuIcon className="h-6 w-6" aria-hidden="true" />
              </button>
            </div>

            {/* Logo (lg-) */}
            <Link to="/" className="lg:hidden">
              <p className="font-pacifico text-4xl text-indigo-600">Eire</p>
            </Link>

            <div className="flex-1 flex items-center justify-end">
              <div className="flex items-center lg:ml-8">
                <CartLink />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
