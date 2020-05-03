import React, { Component } from 'react'
import SirioMainLayout from '../../Layout/SirioMainLayout';
import { withRouter } from 'react-router-dom';
import ErrorDetail from '../../Components/Error/ErrorDetail';

export class ErrorPage extends Component {

    render() {
        const detail = this.props.location.state ? this.props.location.state.detail : "Server mengalami kekeliruan.";
        const code = this.props.location.state ? this.props.location.state.code : "500";
        return (
            <SirioMainLayout transparent>
                <ErrorDetail
                    code={code}
                    detail={detail}
                />
            </SirioMainLayout>
        )
    }
}

export default withRouter(ErrorPage)
