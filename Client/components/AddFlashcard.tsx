import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

const AddFlashcard = () => {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button size="sm">Add</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Add flashcard</DialogTitle>
          <DialogDescription>
            Add new flashcard here. Click save when you&apos;re done.
          </DialogDescription>
        </DialogHeader>

        <DialogFooter>
          <Button type="submit">Save changes</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default AddFlashcard;
