import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import EmployeeService from '../../../Services/EmployeeService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";

class DetailEmployee extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            employee: {},
            dataGeneral: {},
            redirect: false
        };

        this.renderDataEmployee = this.renderDataEmployee.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderDataEmployee();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/employee",
                state: {
                    deleteSuccess: true
                }
            }} />
        }
    };

    async renderDataEmployee() {

        // Mengubah isi dari loader
        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengambil data dari server");

        const response = await EmployeeService.getEmployee(this.props.location.state.id);

        // Mengubah isi dari loader
        this.props.changeLoadingBody("Menampilkan data");

        this.setState({
            employee: response.data.result,
            dataGeneral: {
                "Nama": response.data.result.nama,
                "Jabatan": response.data.result.jabatan,
                "Username": response.data.result.username,
                "Role": response.data.result.role.namaRole,
                "Email": response.data.result.email,
                "Nomor Telepon": this.nomorHpFormatter(response.data.result.noHp)
            }
        }, this.props.contentFinishLoading())
    }

    nomorHpFormatter(noHp) {
        if (noHp) {
            return noHp
        } else {
            return "-"
        }
    }

    hapus(id) {
        const employee = {
            id: id
        };

        this.props.contentStartLoading();
        this.props.changeLoadingBody("Mengirim data ke server");
        EmployeeService.deleteEmployee(employee)
            .then(() => this.setRedirect());
        this.props.contentFinishLoading()
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
                        recommended
                        classes="mx-1"
                    >
                        Ubah
                    </SirioButton>
                </NavLink>
                <SirioWarningButton
                    red
                    classes="mx-1"
                    modalTitle="Konfirmasi Penghapusan"
                    modalDesc="Apakah anda yakin untuk menghapus employee?"
                    onConfirm={() => this.hapus(this.state.employee.idEmployee)}
                    customConfirmText="Ya, Hapus"
                    customCancelText="Batal"
                >
                    Hapus
                </SirioWarningButton>
            </div>
        )
    }

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioDetailPage
                    title="Detail Employee"
                    data={this.state.dataGeneral}
                    id='id'
                    subButton={this.subButton()}
                />
            </>
        );
    }
}

export default withRouter(DetailEmployee);