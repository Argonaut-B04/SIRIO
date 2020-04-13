import React from "react";
import SirioMainLayout from "../Layout/SirioMainLayout";
import DemoMultiForm from "../Components/Form/DemoForm/DemoMultiForm";

/**
 * Controller yang menampilkan halaman demo form
 */
export default class FormMultiPage extends React.Component {
    render() {
        return (
            <SirioMainLayout>
                <DemoMultiForm />
            </SirioMainLayout>
        );
    }
};