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
    <div className="grid h-[500px] ml-72 mt-20 mr-14 grid-rows-[repeat(1,100px)] grid-cols-[repeat(1,1fr)] gap-[1em]  auto-rows-[80px] ">
    {data.map((post) => (
        <DeckCard key={post.id} {...post} />
    ))}
    </div>
  )
}

export default page