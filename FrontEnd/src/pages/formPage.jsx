import React from "react";
import SirioMainLayout from "../Layout/SirioMainLayout";
import TheForm from "./form";

/**
 * Controller yang menampilkan halaman demo form
 */
export default class FormDemo extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <TheForm />
            </SirioMainLayout>
        );
    }
};