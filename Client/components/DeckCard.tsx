import React from "react";
import Link from "next/link";

import { PostProps } from "@/types/types";
import { Card, CardTitle } from "@/components/ui/card";

const DeckCard = ({ title, id }: PostProps) => {
  return (
    <Card className="flex border items-center justify-center h-20">
      <CardTitle>
        <Link href={`/decks/${id}`}> {title} </Link>
      </CardTitle>
    </Card>
  );
};

export default DeckCard;
