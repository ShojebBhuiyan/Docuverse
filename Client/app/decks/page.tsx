import React from 'react'
import { PostProps } from "@/types/types";
import DeckCard from "@/components/DeckCard";


async function getData() {
   const res = await fetch("https://jsonplaceholder.typicode.com/posts");
 
   if (!res.ok) {
     throw new Error("Failed to fetch data");
   }
 
   return res.json();
 }

const page = async () => {
const data: PostProps[] = await getData();
  return (
    <div className="flex flex-wrap mx-36 my-24 gap-9">
    {data.map((post) => (
        <DeckCard key={post.id} {...post} />
    ))}
    </div>
  )
}

export default page