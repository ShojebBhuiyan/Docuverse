'use client'

import {useParams} from 'next/navigation'
import { ChevronRight,ChevronLeft } from "lucide-react"
import { Button } from '@/components/ui/button'
import { useState,useEffect } from 'react'
import { PostProps } from "@/types/types";

const page =  () => {
    const { id } = useParams()
    const [count, setCount] = useState(0);
    const [data, setData] = useState<PostProps[]>();

    useEffect(() => {
      fetch('https://jsonplaceholder.typicode.com/posts')
        .then((res) => res.json())
        .then((data) => {
          setData(data)
          //setLoading(false)
        })
    }, [])
   


    const handleClickLeft = (e: React.MouseEvent<HTMLButtonElement>) => {
      //e.preventDefault();
      count>0 && setCount(count - 1);
    };

    const handleClickRight = (e: React.MouseEvent<HTMLButtonElement>) => {
      //e.preventDefault();
      setCount(count + 1);
    };
    
  return (
    <div>
    <div className='flex p-5 border ml-10 mt-20 w-[800px] h-[400px] justify-center items-center '>  
        <h1>Deck {id} : {count}  </h1>
        {data && <h1>{data[count].title}</h1>}
    </div>
    <div className="flex ml-10 mt-2 w-[800px] justify-center gap-4 ">
    <Button className="" onClick={handleClickLeft} variant="outline" size="icon">
     <ChevronLeft className="h-4 w-4" />
   </Button>
    <Button className="" onClick={handleClickRight} variant="outline" size="icon">
     <ChevronRight className="h-4 w-4" />
   </Button>
   </div>
   </div>
  )
}

export default page