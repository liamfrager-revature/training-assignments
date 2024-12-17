import { fireEvent, render, screen } from '@testing-library/react';
import Counter from './Counter';

describe('counter test', () => {
    test("renders the counter and buttons", () => {
        render(<Counter />);

        const counter = screen.getByText("0")
        const increment = screen.getByText("+")
        const decrement = screen.getByText("-")

        expect(counter).toBeInTheDocument();
        expect(increment).toBeInTheDocument();
        expect(decrement).toBeInTheDocument();
    });

    test("increments the counter when the + button is clicked", () => {
        render(<Counter />);

        const counter = screen.getByText("0")
        const increment = screen.getByText("+")

        fireEvent.click(increment);

        expect(counter).toHaveTextContent("1")
    });

    test("decrements the counter when the - button is clicked", () => {
        render(<Counter />);

        const counter = screen.getByText("0")
        const decrement = screen.getByText("-")

        fireEvent.click(decrement);

        expect(counter).toHaveTextContent("-1")
    });

    test("increments and decrements correctly on multiple clicks", () => {
        render(<Counter />);

        const counter = screen.getByText("0")
        const increment = screen.getByText("+")
        const decrement = screen.getByText("-")

        fireEvent.click(increment);
        fireEvent.click(increment);
        fireEvent.click(decrement);

        expect(counter).toHaveTextContent("1")
    });
})