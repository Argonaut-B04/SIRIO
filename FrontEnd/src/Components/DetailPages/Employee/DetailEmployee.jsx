import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';


class DetailEmployee extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            employee: {}
        }

        this.renderDataEmployee = this.renderDataEmployee.bind(this);
    }

    componentDidMount() {
        this.renderDataEmployee();
    }

    async renderDataEmployee() {
        const response = await EmployeeService.getEmployee(this.props.location.state.id);

        this.setState({
            employee: response.data.result
        })
    }

    nomorHpFormatter() {
        if (this.state.employee.noHp) {
            return this.state.employee.noHp
        } else {
            return "-"
        }
    }

    data() {
        return {
            "Nama": this.state.employee.nama,
            "Jabatan": this.state.employee.jabatan,
            "Username": this.state.employee.username,
            "Email": this.state.employee.email,
            "Nomor Telepon": this.nomorHpFormatter()
        };
    }

    subButton() {
        return (
            <div>
                <NavLink to={{
                    pathname: "/employee/ubah",
                    state: {
                        id: this.state.employee.idEmployee,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Ubah
                    </SirioButton>
                </NavLink>
                <NavLink to={{
                    pathname: "/employee/hapus",
                    state: {
                        id: this.state.employee.idEmployee,
                    }
                }}>
                    <SirioButton
                        purple
                    >
                        Hapus
                    </SirioButton>
                </NavLink>
            </div>
        )
    }

    render() {
        return (
            <SirioDetailPage
                title="Detail Employee"
                data={this.data()}
                id='id'
                subButton={this.subButton()}
            />
        );
    }
}

export default withRouter(DetailEmployee);