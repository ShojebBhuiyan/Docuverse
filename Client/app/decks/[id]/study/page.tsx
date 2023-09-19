'use client'

import {useParams} from 'next/navigation'
import { ChevronRight,ChevronLeft } from "lucide-react"
import { Button } from '@/components/ui/button'
import { useState,useEffect } from 'react'
import { CardProps } from "@/types/types";

const page =  () => {
    const { id } = useParams()
    const [count, setCount] = useState(0);
    const [data, setData] = useState<CardProps[]>();
    const [front, setFront] = useState(true);

    useEffect(() => {
      fetch('http://localhost:8080/api/v1/flashcard/all')
        .then((res) => res.json())
        .then((data) => {
          setData(data)
          console.log(data)
        })
    }, [])
   
    const handleClickLeft = (e: React.MouseEvent<HTMLButtonElement>) => {
      //e.preventDefault();
      count>0 && setCount(count - 1);
    };

    const handleClickRight = (e: React.MouseEvent<HTMLButtonElement>) => {
      //e.preventDefault();
      setFront(true);
      setCount(count + 1);
    };

    
    const handleCard = (e: React.MouseEvent<HTMLDivElement>) => {
      //e.preventDefault();
      setFront(true);
      setFront(!front);
    };
    
  return (
    <div>
    <div className='flex flex-col p-5 border ml-10 mt-20 w-[800px] h-[400px] justify-center items-center' onClick={handleCard}>  
        <h1>Deck {id} : {count}</h1>
        <h1>{front ? 'Front' : 'Back'}</h1>
        <h2>{front ? data?.[count]?.question : data?.[count]?.answer}</h2> 
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