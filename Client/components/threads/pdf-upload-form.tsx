"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { Button } from "../ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "../ui/form";
import { Input } from "../ui/input";

const pdfUploadFormSchema = z
  .object({
    files: z.instanceof(FileList),
  })
  .refine((data) => data.files.length != 0, {
    message: "Choose a file!",
    path: ["file"],
  });

interface PDFUploadFormProps {
  parentOnChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function PDFUploadForm({ parentOnChange }: PDFUploadFormProps) {
  const form = useForm<z.infer<typeof pdfUploadFormSchema>>({
    resolver: zodResolver(pdfUploadFormSchema),
  });

  console.log("Form value: " + form.getValues("files"));

  async function onSubmit(values: z.infer<typeof pdfUploadFormSchema>) {
    try {
      const formData = new FormData();

      // formData.append("threadId", threadId);

      for (let i = 0; i < values.files.length; i++) {
        formData.append(`files`, values.files[i]);
      }

      const response = await axios.post(
        "http://localhost:8080/api/v1/document/upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: "Bearer " + localStorage.getItem("auth-token"),
          },
        }
      );
      console.log("Response from the backend:", response.data);
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <div className="flex gap-1">
          <FormField
            control={form.control}
            name="files"
            render={({ field: { value, onChange, ...fieldProps } }) => (
              <FormItem>
                <FormControl>
                  <Input
                    multiple={true}
                    type="file"
                    accept=".pdf"
                    placeholder="upload your file"
                    {...fieldProps}
                    className="border-input bg-background ring-offset-background placeholder:text-muted-foreground focus-visible:ring-ring flex h-10 w-full rounded-md border px-3 py-2 text-sm file:border-0 file:bg-transparent file:text-sm file:font-medium focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                    onChange={(event) => {
                      parentOnChange(event);
                      onChange(event.target.files && event.target.files);
                    }}
                    // value={field.value}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <Button type="submit">Upload</Button>
        </div>
      </form>
    </Form>
  );
}
