'use client'

import {useParams} from 'next/navigation'

const page = () => {
    const { id } = useParams()
  return (
    <div>
        <h1>Deck {id} </h1>
    </div>
  )
}

export default page