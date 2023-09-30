"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { Button } from "./ui/button";
import { Form, FormControl, FormField, FormItem, FormMessage } from "./ui/form";
import { Input } from "./ui/input";
import { useToast } from "./ui/use-toast";

const signinFormSchema = z.object({
  email: z.string().email(),
  password: z.string(),
});

export default function SignInForm() {
  const form = useForm<z.infer<typeof signinFormSchema>>({
    resolver: zodResolver(signinFormSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const { toast } = useToast();
  const router = useRouter();

  async function onSubmit(values: z.infer<typeof signinFormSchema>) {
    try {
      const result = await fetch("http:localhost:8080/api/v1/auth/signin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: values.email,
          password: values.password,
        }),
      });
      if (result.status === 200) {
        toast({
          variant: "default",
          description: "Login successful!",
        });
        // router.replace("/guided-form");
      } else {
        toast({
          variant: "destructive",
          description: "Invalid credentials",
        });
      }
    } catch (error) {
      console.log(error);
      toast({
        variant: "destructive",
        description: "Something went wrong",
      });
    }
  }

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex w-[20rem] flex-col space-y-2"
      >
        <div className="mb-5 flex justify-center">
          <h1 className="text-4xl font-bold text-[#15bebe]">Sign In</h1>
        </div>
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input
                  placeholder="youremail@xyz.com"
                  {...field}
                  className="border border-black"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <Input
                  type="password"
                  placeholder="your password"
                  {...field}
                  className="border border-black"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" className="justify-center">
          Submit
        </Button>
        <div className="flex gap-2">
          <h3 className="text-lg">Don&apos;t have an account?</h3>
          <Link href={"/signup"} className="text-lg underline">
            Sign Up
          </Link>
        </div>
      </form>
    </Form>
  );
}
