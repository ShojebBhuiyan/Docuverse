"use client"

import { useEffect, useState } from "react"
import { useParams } from "next/navigation"
import { set } from "zod"

import { CardProps } from "@/types/types"
import { Button } from "@/components/ui/button"

const page = () => {
  const { id } = useParams()
  const [count, setCount] = useState(0)
  const [round, setRound] = useState(1)
  const [data, setData] = useState<CardProps[]>()
  const [front, setFront] = useState(true)
  const [masteredCards, setMasteredCards] = useState(0)
  let  totalCards = 0 ;

  useEffect(() => {
    // Function to shuffle the data based on weights
    function weightedShuffle(data: CardProps[]): CardProps[] {
      // Calculate the total weight
      const totalWeight = data.reduce((sum, item) => sum + item.weight, 0)

      // Generate a random number between 0 and the totalWeight
      const randomValue = Math.random() * totalWeight

      // Sort the data based on cumulative weights
      let cumulativeWeight = 0
      data.sort((a) => {
        cumulativeWeight += a.weight
        return cumulativeWeight - randomValue
      })

      return data
    }
    fetch("http://localhost:8080/api/v1/flashcard/all")
      .then((res) => res.json())
      .then((data: CardProps[]) => {
        setData(data)
        console.log(data)

        // Shuffle the data based on weights
        weightedShuffle(data)

        totalCards = data.length;

        let cardsWithWeightZero = Object.values(data).filter((item: CardProps) => item.weight === 0);

        console.log(cardsWithWeightZero.length);

        setMasteredCards(cardsWithWeightZero.length);
        
        // Print the shuffled data
        console.log(data)
      })
  }, [round])

  const updateFlashcardWeight = async (updatedFlashcardData: any) => {
    try {
      console.log(updatedFlashcardData)
      const response = await fetch(
        `http://localhost:8080/api/v1/flashcard/update`, // Modify the URL endpoint to update a specific flashcard
        {
          method: "PUT", // Use PUT or PATCH depending on your API
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(updatedFlashcardData),
        }
      )

      if (response.status === 200) {
        const responseData = await response.json()
        // Handle success or update UI as needed
      } else {
        console.error("Update request failed")
      }
    } catch (error) {
      console.error("An error occurred", error)
    }
  }

  const handleClickIncorrect = (
    e: React.MouseEvent<HTMLButtonElement>,
    id: number
  ) => {
    //e.preventDefault();
    setFront(true)
    const updatedFlashcardData = { ...data?.[count] }
    if (
      updatedFlashcardData?.weight !== undefined &&
      updatedFlashcardData.weight < 3
    ) {
      updatedFlashcardData.weight = updatedFlashcardData.weight + 1
    }
    updateFlashcardWeight(updatedFlashcardData)
    count > (data?.length ?? 0) - 2
      ? (setCount(0), setRound(round + 1))
      : setCount(count + 1);

  }

  const handleClickCorrect = (
    e: React.MouseEvent<HTMLButtonElement>,
    id: number
  ) => {
    //e.preventDefault();
    setFront(true);

    const updatedFlashcardData = { ...data?.[count] };
    if (
      updatedFlashcardData?.weight !== undefined &&
      updatedFlashcardData.weight > 0
    ) {
      updatedFlashcardData.weight = updatedFlashcardData.weight - 1;
    }
    updateFlashcardWeight(updatedFlashcardData);

    count > (data?.length ?? 0) - 2
      ? (setCount(0), setRound(round + 1))
      : setCount(count + 1);

    console.log(count);
  };

  const handleCard = (e: React.MouseEvent<HTMLButtonElement>) => {
    //e.preventDefault();
    setFront(!front)
  }

  return (
    <div className="flex mx-16 my-8">
      <div className="flex flex-col  w-[1000px] h-[450px]  [perspective:1000px] gap-2 ">
        <div
          className={`relative border h-full w-full rounded-xl shadow-xl transition-all duration-1000 [transform-style:preserve-3d] ${
            front ? "" : "[transform:rotateY(180deg)]"
          }`}
        >
          {front ? (
            <div className="flex flex-col items-center absolute inset-0 rounded-xl px-12  [backface-visibility:hidden] pt-5 gap-5">
              <div className="text-xl">`Question${count + 1}`</div>
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
                  onClick={(e) => handleClickCorrect(e, count)}
                  variant="default"
                  size="sm"
                >
                  Correct
                </Button>
                <Button
                  className="hover:bg-red-600"
                  onClick={(e) => handleClickIncorrect(e, count)}
                  variant="destructive"
                  size="sm"
                >
                  Incorrect
                </Button>
              </>
            )}
          </div>
          <Button
            className=" "
            onClick={handleCard}
            variant="default"
            size="sm"
          >
            Flip Card
          </Button>
        </div>
      </div>
      <div className="flex w-[800px] p-4 justify-center items-center gap-4 ">
        <div>Count {count}</div>
        <div>Round {round}</div>
        <div className="w-2/3 bg-gray-200 rounded-full h-3 dark:bg-gray-700">
          <div
            className="bg-green-600 h-3 rounded-full"
            style={{ width: `${(masteredCards ?? 0) * (totalCards/100)}%` }}
          ></div>
        </div>
        <div>You have mastered {masteredCards} cards after round {round -1 }</div>
      </div>
    </div>
  )
}

export default page
