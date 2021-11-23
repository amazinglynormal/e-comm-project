interface Props {
  collectionName: string;
  collectionDescription: string;
}

const CollectionHeader = ({ collectionName, collectionDescription }: Props) => {
  return (
    <div className="border-b border-gray-200 pb-10">
      <h1 className="text-4xl font-extrabold tracking-tight text-gray-900">
        {collectionName}
      </h1>
      <p className="mt-4 text-base text-gray-500">{collectionDescription}</p>
    </div>
  );
};

export default CollectionHeader;
