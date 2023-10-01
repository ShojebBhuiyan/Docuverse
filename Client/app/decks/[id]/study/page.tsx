"use client"

import { useEffect, useState } from "react"
import { useParams } from "next/navigation"
import { ChevronLeft, ChevronRight } from "lucide-react"

import { CardProps } from "@/types/types"
import { Button } from "@/components/ui/button"
import { set } from "zod"

const page = () => {
  const { id } = useParams()
  const [count, setCount] = useState(1)
  const [data, setData] = useState<CardProps[]>()
  const [front, setFront] = useState(true)

  useEffect(() => {
    fetch("http://localhost:8080/api/v1/flashcard/all")
      .then((res) => res.json())
      .then((data) => {
        setData(data)
        console.log(data)
      })
  }, [])

  const handleClickIncorrect = (e: React.MouseEvent<HTMLButtonElement>) => {
    //e.preventDefault();
    setFront(true)
    setCount(count + 1)
    
  }

  const handleClickCorrect = (e: React.MouseEvent<HTMLButtonElement>) => {
    //e.preventDefault();
    setFront(true)
    setCount(count + 1)
  }

  const handleCard = (e: React.MouseEvent<HTMLButtonElement>) => {
    //e.preventDefault();
    setFront(!front)
  }

  return (
    <div className="flex mx-16 my-8">
      <div className="flex flex-col  w-[1000px] h-[450px]  [perspective:1000px] gap-2 ">
        <div
          className={`relative border h-full w-full rounded-xl shadow-xl transition-all duration-500 [transform-style:preserve-3d] ${
            front ? "" : "[transform:rotateY(180deg)]"
          }`}
        >
          {front ? (
            <div className="flex flex-col items-center absolute inset-0 rounded-xl px-12  [backface-visibility:hidden] pt-5 gap-5">
              <div className="text-xl">`Question${count}`</div>
              <div className=" ">{data?.[count]?.question}</div>
            </div>
          ) : (
            <div className="absolute flex flex-col items-center inset-0 rounded-xl px-12 pt-5 gap-5 [transform:rotateY(180deg)] [backface-visibility:hidden]">
              <div className="text-xl">Answer</div>
              <div>{data?.[count]?.answer}</div>
            </div>
          )}
        </div>
        <div className="flex  items-center">
        <div className="flex gap-2 w-[545px] pl-[220px]">
          {front ? (
            <div> </div>
          ) : (
            <>
              <Button
                className="bg-green-500 text-white  hover:bg-green-600 py-2 px-4 rounded-sm"
                onClick={handleClickCorrect}
                variant="default"
                size="sm"
              >
                Correct
              </Button>
              <Button className="hover:bg-red-600" onClick={handleClickIncorrect} variant="destructive" size="sm">
                Incorrect
              </Button>
              </>
            
          )}
          </div>
          <Button className=" hover:bg-black " onClick={handleCard} variant="default" size="sm">
            Flip Card
          </Button>
        </div>
      </div>
      <div className="flex w-[800px] p-4 justify-center gap-4 ">
        <Button
          className=""
          
          variant="outline"
          size="icon"
        >
          <ChevronLeft className="h-4 w-4" />
        </Button>
        <Button
          className=""
          
          variant="outline"
          size="icon"
        >
          <ChevronRight className="h-4 w-4" />
        </Button>
      </div>
    </div>
  )
}

export default page
