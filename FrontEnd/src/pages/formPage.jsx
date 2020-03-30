import React from "react";
import SirioMainLayout from "../Layout/SirioMainLayout";
import DemoForm from "../Components/Form/DemoForm/DemoForm";

/**
 * Controller yang menampilkan halaman demo form
 */
export default class FormDemo extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DemoForm />
            </SirioMainLayout>
        );
    }
};